function pb3()
  x1 = -1; x2 = 1;
  y1 = -3; y2 = 4; 
  dx1 = 0; dx2 = -4;
  range = linspace(x1, x2, 100);
  curve = curveHermite(x1, x2, y1, y2, dx1, dx2, range);
  
  plot(range, curve);
end