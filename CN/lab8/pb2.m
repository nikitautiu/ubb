function pb2()
  % evaluam un spline pe o multime de puncte date
  disp("valorile lui sin in -pi, -pi/2, 0, pi/2, pi aprox cu spline cu der de ord 2");
  disp(spline(-10:10, [-pi, -pi/2, 0, pi/2, pi], sin(-10:10), 1, -sin(-10:10)));
end