function pb1()
  plot(linspace(-4, 4, 100), linspace(-4, 4, 100), 'b-',
       linspace(-4, 4, 100), 2 .* sin(linspace(-4, 4, 100)), 'r-');
  legend("x", "2 * sin(x)");
  f = @(x)  x - 2 .* sin(x);
  fd = @(x) 1 - 2 .* cos(x);
 
  disp("sol cu newton");
  disp([Newton(f, fd,-2, 1e-7), 
    Newton(f, fd, 0, 1e-7), 
    Newton(f, fd, 2, 1e-7)] );
  
  
  disp("sol cu secanta");
  disp([Secanta(f, -2, -1.5, 1e-7), 
    Secanta(f, -0.1, 0.1, 1e-7), 
    Secanta(f, 1.5, 2, 1e-7)] );
  
 
endfunction
