# Compute power for each window length.

#id90_ihs.txt
#inf="s*_ihs.txt"
ss=`seq 500 100 2000`
#l=5
out="power.txt"
>${out}
for s in ${ss}
do
    echo ${s} >> ${out}
    cat s${s}_ihs.txt | node ~/scct/PowerCaculation_near.js 25 2 8 1500000 3 4 300 >>${out}
done   
