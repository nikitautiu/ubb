function [sin]=sin_taylor(x,err)
  x=mod(x,2*pi);
  sin=x;
  termen=-x^2/2;
  k=2;
  while k<100
      k=k+1;
      termen=termen.*x/k;
      sin=sin+termen;
      k=k+1;
      termen=-termen.*x/k;
      if abs(termen)<err
          return;
      end
  end
end
