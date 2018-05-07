function c=splineCoefs(x, f, type, der)
% calculeaza coeficientii spline-ului cubic
% x si y sunt nodurile de interpolare
% type e tipul spline-ului, care are are valori de la 0 la 3
% ce repr complet, cu derivate de ordin 2, natural, si deBoor
% der reprezinta valorile derivatelor:
% prima derivata pt spline complte, a doua pt cel cu derivate de ordin 2
% returneaza coeficientii

  if nargin < 4 || type == 2
	  der = [0, 0]; % spline natural, nu se dau derivatele
    type = 2;
  end 

  n = length(x);
  if any(diff(x) < 0)
	  [x, ind] = sort(x);  % sorteaza nodurile de interpolare crescator 
  else 
    ind=1:n; 
  end


  y = f(ind); x = x(:); y = y(:);

  % derivate prin diferente divizate
  dx = diff(x);
  ddiv = diff(y) ./ dx; 

  ds = dx(1:end-1); 
  dd = dx(2:end); 
  dp =  2 * (ds + dd);                 
  md = 3 * (dd .* ddiv(1:end-1) + ds .* ddiv(2:end)); % termenii liberi
  if type == 0 % complete
      dp1 = 1; 
      dpn = 1; 
      vd1 = 0; 
      vdn = 0;
      md1 = der(1); mdn = der(2);
  elseif type == 1 || type == 2
      dp1 = 2; 
      dpn = 2; 
      vd1 = 1; 
      vdn = 1;
      md1 = 3 * ddiv(1) - 0.5 * dx(1) * der(1);
      mdn = 3 * ddiv(end) + 0.5 * dx(end) * der(2);
  else % deBoor
      x31 = x(3) - x(1);
      xn = x(n) - x(n - 2);
      dp1 = dx(2); 
      dpn = dx(end-1);
      vd1 = x31;
      vdn = xn;
      md1 = ((dx(1) + 2 * x31) * dx(2) * ddiv(1) + dx(1) ^ 2 * ddiv(2)) / x31;
      mdn = (dx(end) ^ 2 * ddiv(end-1) + (2 * xn + dx(end)) * dx(end-1) * ddiv(end)) / xn;
  end

  % cream sistemul de rezolvat
  dp = [dp1; dp; dpn];
  dp1 = [0; vd1; dd];
  dm1 = [ds; vdn; 0];
  md = [md1; md; mdn];

  % cream matricea tridiagonala cu diagonalele date
  A = spdiags([dm1, dp, dp1], -1:1, n, n);
  m = A \ md;
  c(:, 4) = y(1:(end - 1));
  c(:, 3) = m(1:(end - 1));
  c(:, 1) = (m(2:end) + m(1:(end - 1)) - 2 * ddiv) ./ (dx.^2);
  c(:, 2) = (ddiv - m(1:(end - 1))) ./ dx - dx .* c(:, 1);

end
