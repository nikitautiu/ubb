function [res] = my_realmin()
  y=1.;
  min1=1.;
  while y>0
      min1=y;
      y=y/2;
  end
  res=min1;
end
