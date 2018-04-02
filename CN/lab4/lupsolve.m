function x=lupsolve(A, b)
  [L, U, P] = lup(A);
  y = forwardsubst(L, P*b);
  x = backsubst(U, y);
end
