#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#define MAXSIZE 100
char* integer_to_str(int i){
	int length= snprintf(NULL,0,"%d",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%d",i);
	return result;
}
char* double_to_str(float i){
	int length= snprintf(NULL,0,"%f",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%f",i);
	return result;
}
char* char_to_str(char i){
	int length= snprintf(NULL,0,"%c",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%c",i);
	return result;
}
char* bool_to_str(bool i){
	int length= snprintf(NULL,0,"%d",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%d",i);
	return result;
}
char* str_concat(char* str1, char* str2){
	char* result=malloc(sizeof(char)*MAXSIZE);
	result=strcat(result,str1);
	result=strcat(result,str2);
	return result;
}

char* read_str(){
	char* str=malloc(sizeof(char)*MAXSIZE);
	scanf("%s",str);
	return str;
}

int str_to_bool(char* expr){
	int i=0;
	if ( (strcmp(expr, "true")==0) || (strcmp(expr, "1"))==0 )
		i=1;
	if ( (strcmp(expr, "false")==0) || (strcmp(expr, "0"))==0 )
		i=0;
	return i;
}

int a = 0;
int b = 0;
int gcd = 0;
void calculateGCD ()
{
int temp2 = 0;
int temp1 = 0;
temp1 = a;
temp2 = b;
while(temp1 != temp2)
{
if(temp1 > temp2){
temp1 = temp1 - temp2;
} else {
temp2 = temp2 - temp1;
}
}
gcd = temp1;
}
double calculateLCM ()
{
return a * b / gcd;
}
int main() {
double lcm = 0.0;
printf("%s\n", "Inserisci due numeri interi positivi: ");
scanf("%d", &a);
scanf("%d", &b);
calculateGCD();
lcm = calculateLCM();
printf("%s\n", str_concat(str_concat(str_concat(str_concat(str_concat("Il massimo comune divisore (GCD) di ", integer_to_str(a)), " e "), integer_to_str(b)), " è: "), integer_to_str(gcd)));
printf("%s\n", str_concat(str_concat(str_concat(str_concat(str_concat("Il minimo comune multiplo (LCM) ", integer_to_str(a)), " e "), integer_to_str(b)), " è: "), double_to_str(lcm)));
}
