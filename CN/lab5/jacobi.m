function [x,ni] = jacobi(A,b,x0,ea,er,nitmax) 
 M=diag(diag(A));
 N=M-A;
 for k=1:nitmax
   x=M\(N*x0+b);
   if norm(x-x0)<(ea-er*norm(x))
     ni = k;
     return
   end
   x0=x;
 end
end