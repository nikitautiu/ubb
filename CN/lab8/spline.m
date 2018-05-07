function yy=spline(x, xx, y, type, der)
  % calculeaza spline pt functiile dae, in punctele data
   if nargin < 4 || type == 2
	  der = [0, 0]; % spline natural, nu se dau derivatele
    type = 2;
  end 

  c = splineCoefs(x, y, type, der);
  yy = evalSpline(x, c, xx);
end
