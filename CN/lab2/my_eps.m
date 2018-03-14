function [res] = my_eps() % calcul epsilon-masina
  eps1=1;
  while 1~=1+eps1
      eps1=eps1/2;
  end
  eps1=2*eps1;
  res=eps1;
end