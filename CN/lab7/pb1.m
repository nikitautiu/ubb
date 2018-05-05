function pb1()
  A=[0 1.3 0.6200860 -0.5220232
    1 1.6 0.4554022 -0.5698959
    2 1.9 0.2818186 -0.5811571];
    
  disp("Valoare functiei in 1.5");
  disp(intHermite(A(:,2),A(:,3),A(:,4), 1.5));
end