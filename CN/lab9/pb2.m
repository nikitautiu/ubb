function pb2()
  x = [-1.024940 -0.949898 -0.866114 -0.773392 -0.671372 -0.559524 -0.437067 -0.302909 -0.159493 -0.007464];
  y = [-0.389269 -0.322894 -0.265256 -0.216557 -0.177152 -0.147582 -0.128618 -0.121353 -0.127348 -0.148895];
  A = [(y .^ 2)' (x .* y)' (x + 1)' y']
  b = (x .^ 2)'; 
  disp("coeficientii pt prima ecuatie");
  disp(A \ b);
  
  A = [y' ones(length(y), 1)]
  b = (x .^ 2)'; 
  disp("coeficientii pt a doua ecuatie");
  disp(A \ b);
endfunction
