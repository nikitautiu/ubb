function [res] = my_realmax()
  y=1.;
  max1=1.;
  while y!=Inf 
      max1=y;
      y=y*2;
  end
  res=max1;
end
