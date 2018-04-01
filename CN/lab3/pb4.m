function pb4()
  n = 20;
  coefs = 2 .^ -(1:n);
  printf("a) cond=%d\n", condPoly(coefs, 1000))
end