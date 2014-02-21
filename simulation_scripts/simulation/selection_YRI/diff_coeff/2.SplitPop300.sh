# 1. Split according to populations.
# 2. Extract 300 replicates for each population.
# (120+4) * 300 = 37200

#AFR_CEU.sel_yri.1300.gz
ss=`seq 500 100 2000`
for s in ${ss}
do
    #let "n=${s}*2"
    #let "h=(${n}+4)*300"
    echo "zcat AFR_CEU.sel_yri.${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 1 120 | head -n 37200 | gzip >r300_yri.${s}.gz"
    
    #let "e1=${n}+1"
    #let "e2=${n}*2"
    echo "zcat AFR_CEU.sel_yri.${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 121 240 | head -n 37200 | gzip >r300_ceu.${s}.gz"
done
