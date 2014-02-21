# Compute power for each window length.

#id90_ihs.txt
#inf="s*_ihs.txt"
ss=`cat para.sh`
l=3
out="causal_${l}.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    cat s${s}_ihs.txt | node ~/scct/LocateCausalSite.js 25 2 8 1500000 3 4 ${l} >>${out}
done   
