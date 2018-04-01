function y=pb2()

for n=10:15
  printf('conditionare pt matricea Hilbert de n=%d este %f\n', n, cond(hilb(n)));
end

