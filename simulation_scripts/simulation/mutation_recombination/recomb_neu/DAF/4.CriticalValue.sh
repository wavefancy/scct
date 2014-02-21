# Compute critical value

p=(0.291666 0.291666 0.291666 0.291666)
ss=(`cat ../para.sh`)
out="critical.txt"
>${out}
for((i=0;i<${#ss[@]};i++)){

    echo ${ss[i]} >>${out}
    zcat yri_ceu.daf.id${ss[i]}.gz | node ~/scct/ComputeWindowedCriticalValue.js  51 51 ${p[i]} 4 >>${out}
    echo "" >>${out}

}
