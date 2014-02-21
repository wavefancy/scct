# 1. Split according to populations.

#AFR_CEU.neu.n210.gz
ss=`cat para.sh`
for s in ${ss}
do
#    let "n=${s}*2"
#    let "h=(${n}+4)*300"
    echo "zcat AFR_CEU.neu.n${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 1 120 | gzip >yri.${s}.gz"
    
    #let "e1=${n}+1"
    #let "e2=${n}*2"
    echo "zcat AFR_CEU.neu.n${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 121 240 | gzip >ceu.${s}.gz"
done
