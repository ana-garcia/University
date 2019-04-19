#include <cstdio>
#include <iostream>

using namespace std;

int main()
{		
	
	int v;
	int x;
	while(cin>>v>>x){
		int resultado=0;
		int z;
		int count;
		printf("%d %d ", v, x);
		if(v>x){
			int a = v;
			v=x;
			x= a;
		}
		
		int i;
		for(i=v; i<=x; i++){
			count=1;
			z=i;
			while(z!=1){
				if(z%2==0){
					z=z/2;
				}
				else{
					z=3*z+1;
				}
				count++;
			}
			if(resultado<count){
				resultado=count;
			}
		
		}
		
		

		printf("%d\n", resultado);
	}
}