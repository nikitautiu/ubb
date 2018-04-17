function pb4(n)
  A = rand(n, n);
  A = A + A';
  A = A + eye(n) * n;
  b = A * ones(n, 1);
  
  printf('Sistem generat\n');
  disp([A b]);
  
  % rezolvare cu cholesky
  R = cholesky(A);
  y = forwardsubst(R', b);
  x = backsubst(R, y);
  
  printf('Solutia: \n');
  disp(x);
end
