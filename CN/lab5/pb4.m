function pb4(n)
    % construieste matricea
    A = randi([0, n+1], [n,n]);
    A = A + eye(n) * 50;
    b = A * (1:n)';
    
    disp('MATRICEA SISTEMULUI ');
    disp([A, b]);
    
    disp("SOLUTII");
    x = jacobi(A, b, ones(1,n)', 0.001, 0.001, 100);
    disp('Jacobi: ');
    disp(x);
    x = sor(A,b, ones(1,n)', 1.234, 0.001, 0.001, 100);
    disp('SOR: ');
    disp(x);
    x = gaussSeidal(A, b, ones(1,n)',0.001,0.001,100);
    disp('Gauss Seidal: ');
    disp(x);

end