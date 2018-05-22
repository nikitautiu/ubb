function [x,ni]=Secanta(f,x0,x1,ea,er,niMax)
%Secanta - metoda Secantei pentru ecuatii in R
%APEL [x,ni]=Secanta(f,x0,x1,ea,er,niMax) UNDE
%       f - functia pentru care se cauta radacini
%       x0,x1 - valori de pornire
%       ea - eroarea absoluta
%       er - eroarea relativa
%       niMax - numarul maxim de iteratii
%       x - aproximatia radacinii
%       ni - numar de iteratii

    if nargin<6
        niMax=50; 
    end
    if nargin<5
        er=0; 
    end
    if nargin<4
        ea=1e-3; 
    end
    xv = x0; 
    fv=feval(f,xv); 
    xc=x1; 
    fc=feval(f,xc);
    for iteratie = 1:niMax
        xn = xc-fc*(xc-xv)/(fc-fv);
        if abs(xn-xc) < ea + er*xn
            x = xn;
            ni = iteratie;
            return
        end
        xv = xc; 
        fv = fc; 
        xc = xn; 
        fc = feval(f,xn);
    end
    error('numarul maxim de iteratii depasit')