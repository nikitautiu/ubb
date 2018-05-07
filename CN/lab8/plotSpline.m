function plotSpline(x, xx, y, t, der)
  if nargin < 4 || t == 2
	  der = [0, 0]; % spline natural, nu se dau derivatele
    t = 2;
  end 

  plot(xx, spline(x, xx, y, t, der));  
end