function Q = adquad(f,a,b,ea)
  c = (a + b)/2;
  fa = f(a); fc = f(c);
  fb = f(b);

  Q = quadstep(f, a, b, ea, fa, fc, fb);
endfunction

function Q = quadstep(f,a,b,ea,fa,fc,fb)
  % functie recursiva pt quadraturi
  h = b - a; 
  c = (a + b)/2; % centru
  fd = f((a+c)/2);
  fe = f((c+b)/2);
  Q1 = h/6 * (fa + 4*fc + fb);
  Q2 = h/12 * (fa + 4*fd + 2*fc + 4*fe + fb);
  if abs(Q2 - Q1) <= ea
     Q  = Q2 + (Q2 - Q1)/15;
     fcount = 2;
  else
     Qa = quadstep(f, a, c, ea, fa, fd, fc);
     Qb = quadstep(f, c, b, ea, fc, fe, fb);
     Q  = Qa + Qb;
  end
endfunction