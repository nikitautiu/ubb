function y=pb3()
printf('cond pt matricea Vandermond cu norma Cebyshev\n');
printf('a)\n');
for n=10:15
  t = linspace(-1,1,n);  % puncte echidistante intre -1 si 1
  printf('n = %d cond = %f \n', n, cond(vander(t), inf));
end

printf('b)\n');
for n=10:15
  t = 1 ./ (1:n)';  % puncte echidistante intre -1 si 1
  printf('n = %d cond = %f \n', n, cond(vander(t), inf));
end