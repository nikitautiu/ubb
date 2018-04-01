function pb1()

A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10];
b = [32; 23; 33; 31];
xe = A\b;

% a) 
bp = [32.1; 22.9; 33.1; 30.9];
xep = A\bp;
err_in1 = norm(b-bp)/norm(b); % -> eroare la intrare
err_out1 = norm(xep-xe)/norm(xe); % -> eroare la iesire

disp('Eroarea relativa pt b='); % -> raportul erorilor
disp(bp);
disp('este');
disp(err_out1/err_in1);

disp('\n');
% b)
Ap = [
  10 7 8.1 7.2;
  7.8 5.04 6 5;
  8 5.98 9.89 9;
  6.99 4.99 9 9.98
];
xep = Ap\b;
err_in1 = norm(A-Ap)/norm(A); % -> eroare la intrare
err_out1 = norm(xep-xe)/norm(xe); % -> eroare la iesire

disp('Eroarea relativa pt A='); % -> raportul erorilor
disp(Ap);
disp('este');
disp(err_out1/err_in1);

  
end