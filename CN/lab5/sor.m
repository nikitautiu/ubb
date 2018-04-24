function [x,ni] = sor(A,b,x0,w,ea,er,nitmax)
 D = diag(diag(A));
 L = D-tril(A);
 
 M=D/w - L;
 N=M-A;
 T=M\N;
 c=M\b;
 
 for k=1:nitmax
   x=T*x0+c;
   if norm(x-x0)<ea-er*norm(x)
     ni = k;
     return;
   end
   x0=x;
 end
end