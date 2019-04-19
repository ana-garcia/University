#include <cstdio>
#include <iostream>
#include <cmath>

using namespace std;

int main()
{		
	long long int v;
	long long int x;
	while(EOF != scanf ("%lld %lld", &v, &x)){
		if(v > x)
			printf("%lld\n", v - x);
		else
			printf("%lld\n", x - v);
	}
}