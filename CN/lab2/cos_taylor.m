function [cos]=cos_taylor(x,err)
x=mod(x,2*pi);
cos=1;
termen=-x^2/2;
k=2;
while k<100
    cos=cos+termen;
    k=k+1;
    termen=termen.*x/k;
    k=k+1;
    termen=-termen.*x/k;
    if abs(termen)<err
        return;
    end
end