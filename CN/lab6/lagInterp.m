function yAprox=lagInterp(x,y,xAprox)
% construieste polinomul de interpolare Lagrange
% x, y - datele de interpolat, 
% xAprox - punctele in care se evalueaza polinomul	
	m=length(x);
	mAprox = length(xAprox);
	yAprox = zeros(mAprox, 1);
	eps=1e-5;
	for k=1:mAprox
		vx=xAprox(k);
		D(1,1)=y(1);
		P(1)= D(1,1);
		S(1)=1;
		for i=2:m
			D(i,1)= y(i);
			for j=1:i-1
				Z(i,j)= x(i)-x(j);
			end
				for j=2:i
					D(i,j)=(D(i,j-1)-D(i-1,j-1))/Z(i,i-j+1);
				end
				S(i)= S(i-1)*abs(x(i-1)-vx);
				P(i)=P(i-1)+S(i)*D(i,i);
			if abs(P(i)-P(i-1)) < eps || i == m  
				yAprox(k)=P(i);
			end
		end	
	end
 end     