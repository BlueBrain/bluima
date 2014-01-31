function a=reorder_matrix(m)
% reorders rows and cols of a matrix so as to maximize
% its diagonal
a=m;
[ N, M ] = size(a);
d=min(N,M);
for i=1:d
  b = a(i:N,i:M);
  [p,q] = max(max(b'));
  a([i q+i-1],:)=a([q+i-1 i],:);
  [p,q]=max(a(i,i:M));
  a(:,[i q+i-1])=a(:,[q+i-1 i]);
end

if (d < N)
  a(d+1:N,:) = sortrows(a(d+1:N,:),-1);
elseif (d < M)
  a(:,d+1:M) = sortrows(a(:,d+1:M)',-1)';
end
