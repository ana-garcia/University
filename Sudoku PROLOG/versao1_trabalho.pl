/* -*- Mode:Prolog; codin+g:iso-8859-1; -*- */

/**
 * Grupo: G16
 * Alunos:
 *     Ana Filipa Rodrigues Garcia, Nº40436
 *     Ricardo Carvalho Martins, Nº32348
 */

%*******************************************************************************************

%gate(+Type, +In, -Out, +N, +Logic, +InfoIn, -InfoOut) gate genérica, que tanto pode servir 
%para logica de simulação como para diagnostico.

%Type: and, input, or, not ...
%In: lista de entradas
%Out: resultado após passar na gate
%N: identificador da gate no sistema
%logic: escrever, ler, fazer...
%InfoIn: A(N-1) usado na logica do simular(sim) para comunicar
%InfoOut: A(N)

% para simular falhas
gate(_, _, Out, N, sim,[N/Out|T], T):-!.        
gate(Type,In, Out, _,sim,X,X):-
        sim_gate(Type,In,Out).     

%para executar normalmente
gate(Type,In, Out, _,executar,_,_):-
        sim_gate(Type,In,Out).   

%para diagnosticar
gate(Type, In, Out, N, diagnosticar, _, _):-
        diag_gate(Type,In,Out,N).

%*************************************************************************************************

%sim_gate(+Type, +In, -Out)
%Type: tipo de gate (and, or, ...)
%In: vetor de entrada
%Out: saida
sim_gate(Type,In,Out):-
        F =.. [Type,In,Out],
        call(F).


%*******************************************************************************************

%simula_falhas(+In, +Diags, -Diag, -Out) simula falhas
%In: vector de entrada
%Diags: lista de possiveis diagnosticos, ou seja, cada diagnostico uma lista ordenada de falhas
%Diag: diagnostico corrente
%Out: out com esse diagnostico
simula_falhas(In,Diags,Diag,Out):-
        nonvar(In), \+ground(In),!,
        aux_listaEntradas(In,InX), 
        simula_falhas_2(InX,Diags,Diag,Out).
simula_falhas(In,Diags,Diag,Out):-
        simula_falhas_2(In,Diags,Diag,Out).

%simula_falhas_2(+In,+Diags,-Diag.-Out) auxilia simula_falhas
simula_falhas_2(In,Diags, Diag, Out):-
       member(Diag,Diags) , circuit(In, Out, sim, Diag, _). 

%aux_listaEntradas(+ListaInComVar, -ListaOutSemVar)
%unifica com x todas as variaveis livres que existirem na lista.
aux_listaEntradas([],[]).
aux_listaEntradas([H|T],[H|X]):-
        var(H),!, H=x,
        aux_listaEntradas(T,X).
aux_listaEntradas([H|T],[H|X]):-
        aux_listaEntradas(T,X).
    

%*************************************************************************************************
%teste(+In, +Diag) teste true se bom teste.
%In: lista de entradas
%Diag: lista de falhas para um teste
teste(In,Diag):-
        nonvar(In), \+ ground(In), !,
        aux_listaEntradas(In,InX), 
        teste_2(InX,Diag).
teste(In,Diag):-
    teste_2(In,Diag).

%teste_2(+In, -Out) auxilia teste
teste_2(In,Diag):- 
        circuit(In, Out1, sim, Diag, _),
        circuit(In, Out2, executar, _, _),
        aux_teste(Out1,Out2).

%aux_teste(L1,L2) vai percorrendo a lista, assim que encontra um 'bom teste' termina.
aux_teste([0|_],[1|_]):-!.
aux_teste([1|_],[0|_]):-!.
aux_teste([_|A],[_|B]):-
        aux_teste(A,B).
                      

%**************************************************************************************************
%Diagnostico(+In, +Out, -Diag)
%In: lista de entradas sem variaveis(x)
%Out: lista da saida real sem variaveis(x)
%Diag: diagnostico das falhas simples possiveis correspondentes
diagnostico(In,Out,Diag):-
        circuit(In, Out2, diagnosticar, _, _),
        lista_diferencas(Out,Out2,LD,X),
        aux_diagnostico(Diag,LD,X).

aux_diagnostico( [], _,0):-!.%o 0 tanto faz, unifica com variavel que vem. podia ser um bla
aux_diagnostico( Diag,LD,1):-
        or(diag,LD,_:Diag), % a logica de saida é igual à do or.
        Diag \= [].
       
%lista_diferencas(+Out,+Out2,-LD) percorre listas, onde encontra diferencas diz ser 1,
%se for igual diz ser 0. com esta modanca pode ser implementada a logica do or na Saida,
%pois o caso da saida real ser igual a suposta é abordada antes.
lista_diferencas([],[],[],_):-!.
lista_diferencas([H|L1],[H:A|T],[0:A|RT],X):-!,
        lista_diferencas(L1,T,RT,X).
lista_diferencas([_|L1],[_:A|T],[1:A|RT],X):-
        lista_diferencas(L1,T,RT,X), X=1.
                   
%diag_gate(+Type, +In, -Out, +Name)
%Type: tipo de gate (and, or, ...)
%In: no formato lista de B:L B-valor normal L-lista ordenada de falhas
%Out no mesmo formato de in
%Name: e' o nome da gate 

%input difere na sua entrada, pois esta provem directamente do vetor de 
%entrada do circuito que não se encontra no formato que decidimos abordar/usar
diag_gate(input,[In],In:[Name/Out],Name):-
        !, not([In],Out).

diag_gate(Type, In, A:Out, Name):-
        F =.. [Type,diag,In,A:Out2],
        call(F),
        not([A],B),
        append(Out2,[Name/B],Out).
                                 
%*************************************************************************************************
%predicados auxiliares do diagnóstico

%intersecao(+A,+B,-L) (AnB)
intersecao(A,B,L):-
        setof(X, (member(X,A), member(X,B)), L), !.
intersecao(_,_,[]).

%uniao(+A,+B,-L) (AuB)
uniao(A,B,L):-
        setof(X, (member(X,A); member(X,B)), L), !.
uniao(_,_,[]).

%exepto(+A,+B,-L) (A\B)
exepto(A,B,L):-
         setof(X, (member(X,A), \+ member(X,B)), L), !.
exepto(_,_,[]).

%dijuncao (XUY)\(XnY)
%dijuncao(+A, +B, -L)
dijuncao(A,B,L):-
        uniao(A,B,L1),
        intersecao(A,B,L2),
        exepto(L1,L2,L).

%*******************************************************************************************

%input(+In, -Out)
input([0],0).
input([1],1).
input([x],x).

%not(+In, -Out)
not([0],1).
not([1],0).
not([x],x).

%not diagnostico
%not (+Logic, +ListaIn, -A:B)
not(diag,[0:L],1:L).
not(diag,[1:L],0:L).

%Buffer
%buffer(+ListaIn, -Out)
buffer([0],0).
buffer([1],1).
buffer([x],x).

%buffer diagnostico
%buffer (+Logic, +ListaIn, -A:B)
buffer(diag,[H],H).

%Xor (lista sempre de tamanho 2)
%xor(+L,-Out)
xor([1,1],0).
xor([1,0],1).
xor([0,1],1).
xor([0,0],0).
xor([x,_],x).
xor([_,x],x).

%xor Diagnostico 
%xor (+Logic, +ListaIn, -A:B)
xor(diag,[H:A,H:B],0:L):-!,                         
        dijuncao(A,B,L).

xor(diag,[_:A,_:B],1:L):-
        dijuncao(A,B,L).

%and(+Lista, -Out)
and(L,Out):-
        and_aux(L,1,Out).

and_aux([],X,X):-!.
and_aux([0|_],_,0):-!.
and_aux([1|T],1,Out):-!,
        and_aux(T,1,Out).
and_aux([_|T],_,Out):-
        and_aux(T,x,Out).

%and diagnostico
%and(+Logic, +L1, -A:B)
and(diag, L1,L2):-
        aux_and_diag(L1,1:[],L2).

aux_and_diag([],L,L):-!.
aux_and_diag([1:L1|T], 1:L2, LFinal):- !,
        uniao(L1,L2,L3),
        aux_and_diag(T,1:L3, LFinal).
aux_and_diag([0:L1|T], 1:L2, LFinal):- !,
        exepto(L1,L2,L3), 
        aux_and_diag(T,0:L3,LFinal).
aux_and_diag([1:L1|T], 0:L2, LFinal):- !,
        exepto(L2,L1,L3), 
        aux_and_diag(T,0:L3,LFinal).
aux_and_diag([_:L1|T], _:L2, LFinal):- 
        intersecao(L1,L2,L3), 
        aux_and_diag(T,0:L3,LFinal).

%nand(+L, -Out)
nand(L,Out):-
        and(L,X),
        not([X],Out).

%nand diagnostico
%nand(+Logic, +L1, -A:B)
nand(diag, L1, B:L2):-
        and(diag,L1,A:L2), 
        not([A],B).

%or(+L, -Out)
or(L,Out):-
        or_aux(L,0,Out).

or_aux([],X,X):-!.
or_aux([1|_],_,1):-!.
or_aux([0|T],0,Out):-!,
        or_aux(T,0,Out).
or_aux([_|T],_,Out):-
        or_aux(T,x,Out).

%or diagnóstico
%or(+Logic, +L1, -A:B)
or(diag,L1,L2):-
        or_aux_diag(L1,0:[],L2).

or_aux_diag([],L,L):-!.
or_aux_diag([0:L1|T], 0:L2, LFinal):- !,
        uniao(L1,L2,L3), 
        or_aux_diag(T,0:L3,LFinal).
or_aux_diag([0:L1|T], 1:L2, LFinal):- !,
        exepto(L2,L1,L3), 
        or_aux_diag(T,1:L3,LFinal).
or_aux_diag([1:L1|T], 0:L2, LFinal):- !,
        exepto(L1,L2,L3), 
        or_aux_diag(T,1:L3,LFinal).
or_aux_diag([_:L1|T], _:L2, LFinal):- 
        intersecao(L1,L2,L3), 
        or_aux_diag(T,1:L3,LFinal).

%nor(+L, -Out)
nor(L,Out):-
        or(L,X),
        not([X],Out).

%nor diagnostico
%nor(+Logic, +L1, -A:B)
nor(diag,L,B:L2):-
        or(diag,L,A:L2),
        not([A],B).

