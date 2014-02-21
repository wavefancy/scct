
#inf="r300_*_coeff.YRI.gz"

inf="r300_yri.*.gz"

out="summary_r300_yri.txt"
>${out}
for f in ${inf}
do
    echo "" >> ${out}
    echo ${f} >> ${out}
    perl ~/perl/getfre.pl ${f} 0.5 1 120 > temp.txt
    wc -l temp.txt >> ${out}
    tail -1 temp.txt >> ${out}
done
