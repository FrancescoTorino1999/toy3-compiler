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

double number = 0.0;
double sum = 0.0;
double divideByTen (double num, double *remainder)
{
int result = 0;
double divisor = 0.0;
*remainder = num;
divisor = 1.0;
while(divisor <= num)
{
divisor = divisor * 10;
}
divisor = divisor / 10;
while(divisor > 0)
{
if(*remainder >= divisor){
*remainder = *remainder - divisor;
result = result + 1;
} else {
divisor = divisor / 10;
result = result * 10;
}
}
return result / 10;
}
void calculateSum ()
{
double temp = 0.0;
double remainder = 0.0;
temp = number;
sum = 0;
while(temp > 0)
{
divideByTen(temp, &remainder);
sum = sum + remainder;
temp = temp - remainder;
temp = temp / 10;
}
}
int main() {
printf("%s\n", "Inserisci un numero positivo: ");
scanf("%lf", &number);
if(number <= 0){
printf("%s\n", "Errore: Inserisci un numero positivo.");
}
calculateSum();
printf("%s", str_concat("La somma delle cifre è: ", double_to_str(sum)));
}
