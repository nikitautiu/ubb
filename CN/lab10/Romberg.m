function I=Romberg(f,a,b,ea,nmax)
  R=zeros(nmax,nmax);
  h=b-a;
  R(1,1)=h/2*(sum(f([a,b])));
  for k=2:nmax
     %formula trapezelor;
     x=a+([1:2^(k-2)]-0.5)*h;
     R(k,1)=0.5*(R(k-1,1)+h*sum(f(x)));
     %extrapolare
     plj=4;
     for j=2:k
        R(k,j)=(plj*R(k,j-1)-R(k-1,j-1))/(plj-1);
        plj=plj*4;
     end
     if (abs(R(k,k)-R(k-1,k-1))<ea)&&(k>3)
        I=R(k,k);  % am atins eroarea
        return
     end
     %dublare noduri
     h=h/2;
  end
endfunction