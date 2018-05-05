function a = grafic()

x=1:0.01:2;

plot(v([1,1.1,1.2,1.3,1.4],[e(1),e(1.1),e(1.2),e(1.3),e(1.4)],x),'green');
hold on;
plot(e(x),'red');

end