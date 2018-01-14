%{
#include<stdlib.h>
#include<string.h>
#include<math.h>

typedef struct{
  int id1;
  int id2;
}PIF;

typedef struct{
  char name[250];
}TS;

int pifLength = 0;
int tsLength = 0;
int tsCode = 0;
PIF program[500];

TS ts[60];

void addPIF(int id1,int id2){
  program[pifLength].id1 = id1;
  program[pifLength].id2 = id2;
  ++pifLength;
}


void addTS(char *atom){
  int found = 0;
  int i = 0;
  while(i < tsLength){
    int res = strcmp(ts[i].name,atom);
    if(res == 0){
      found = 1;
      addPIF(tsCode,i);
    }
    ++i;
  }
  if(found == 0){
    strcpy(ts[tsLength].name,atom);
    addPIF(tsCode,tsLength);
    tsLength++;
  }
}

void showPIF(){
  printf("PIF:\n");
  int t;
  for(t = 0; t < pifLength;++t){
      printf(" %d|%d ",program[t].id1,program[t].id2);
  }
}


void showTS(){
    printf("Identifier\n");
    for(int i = 0 ;i<tsLength;++i)
        printf(" %s|%d", ts[i].name, i);
    printf("\n");
}

void show(){
  showTS();
  showPIF();
}
%}

%option noyywrap
DIGIT     [0-9]
ID_FIRST  [a-zA-Z]
ID_END    [a-zA-Z0-9_]

%%
{DIGIT}+              	addTS(yytext);
{DIGIT}+"."{DIGIT}*   	addTS(yytext);

\identifier           	addPIF(0,-1);
\constant             	addPIF(1,-1);
"include"              	addPIF(2,-1);
"main"                 	addPIF(3,-1);
"int"                 	addPIF(4,-1);
"float"                	addPIF(5,-1);
"struct"               	addPIF(6,-1);
"if"                   	addPIF(7,-1);
"else"                	addPIF(8,-1);
"while"                	addPIF(9,-1);
"="          	          addPIF(10,-1);
"+="            	      addPIF(11,-1);
"-="    		            addPIF(12,-1);
"*="                   	addPIF(13,-1);
"/="                   	addPIF(14,-1);
"++"                   	addPIF(15,-1);
"--"                   	addPIF(16,-1);
"+"                    	addPIF(17,-1);
"-"                    	addPIF(18,-1);
"*"                    	addPIF(19,-1);
"/"                    	addPIF(20,-1);
"%"                    	addPIF(21,-1);
"("                    	addPIF(22,-1);
")"                   	addPIF(23,-1);
"{"                   	addPIF(24,-1);
"}"                   	addPIF(25,-1);
";"                   	addPIF(27,-1);
"=="                	addPIF(28,-1);
"!="                	addPIF(29,-1);
"<"			addPIF(30,-1);
"<="			addPIF(31,-1);
">"			addPIF(32,-1);
">="			addPIF(33,-1);
"&&"			addPIF(34,-1);
"||"			addPIF(35,-1);
"return"		addPIF(36,-1);
"cin"			addPIF(37,-1);
"cout"			addPIF(38,-1);
{ID_FIRST}{ID_END}*   	addTS(yytext);

[ \t\n]+          ;/* eat up whitespace */
. {
  if(yytext[0] != 13){
  printf("Illegal token %s!\n",yytext);
  }
}
%%
int main(int argc, char** argv)
{
  yyin = fopen(argv[1], "r");
  yylex();
  show();
  return 0;
}
