xdata = 1900 : 10 : 2010;
ydata = [75995 91972 105710 123200 131670 150700 179320 203210 226510 249630 281420 308790];

x1 = polyfit(xdata, ydata, 3)       % [c3 c2 c1 c0]
x2 = polyfit(xdata, log(ydata), 1); % [lambda K]
x2(2) = exp(x2(2));

times = linspace(xdata(1),xdata(end));
plot(xdata, ydata,                                                       'ko',
     times, x1(4) + x1(3) * times + x1(2) * times.^2 + x1(1) * times.^3, 'b-',
     times, x2(2) * exp(times*x2(1)),                                    'r-');
legend('date', 'model polinomial', 'model exponential');
title('Populatia SUA');

printf('c0 = %g, c1 = %g, c2 = %g, c3 = %g\n', x1(1), x1(2), x1(3), x1(4));
printf('K = %g, lambda = %g\n', x2(2), x2(1));