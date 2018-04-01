function res=condPoly(coefs, n=1)
  res = zeros(n, length(roots(coefs)));
  for i = 1:n
    r1 = roots(coefs); %radacinile polinomului c
    % genereaza un vector de valori distribuite uniform centrate in 0 cu disp 10^-10
    c2 = coefs + normrnd(0, 1e-10, 1, length(coefs));
    r2 = roots(c2);
    for j=1:length(r2)
      res(i, j) = norm(r2(j)-r1(j))/norm(c2-coefs); % nr de cond  
  end
  res = mean(res);
end