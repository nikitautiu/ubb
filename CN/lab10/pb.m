function pb()
  f = @(x) sin(x);
  plot(linspace(-pi, pi), f(linspace(-pi, pi)));
  
  disp("integrala pe (-pi, pi) si (0, pi) cu Romberg");
  disp([Romberg(f, -pi, pi, 1e-8, 1000) Romberg(f, 0, pi, 1e-8, 1000)]);
  
  disp("integrala pe (-pi, pi) si (0, pi) cu adquad");
  disp([adquad(f, -pi, pi, 1e-8) adquad(f, 0, pi, 1e-8)]);
endfunction
