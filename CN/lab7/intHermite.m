function aprox=intHermite(x, y, dy, xx)
  % returneaza valorile polinomului de interpolare Hermite
  % in punctele date
  
  herTable = hermiteTable(x, y, dy);
  q = herTable(1, 2:end);
  aprox = zeros(1, length(xx));
  
  for i = 1:length(aprox)
    coef = 1.;  % coeficientul actual
    for j = 1:length(q)
      aprox(i) += coef * q(j);  % adauga la valoare polinomului
      coef *= (xx(i) - x(ceil(j/2))); 
    end
  end
  
end