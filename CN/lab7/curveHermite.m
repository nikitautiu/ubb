function vals = curveHermite(x1, x2, y1, y2, dx1, dx2, range)
  % returneaza curba parametrica cu punctele si tangentele date
  vals = intHermite([x1 x2], [y1, y2], [dx1 dx2], range);
end