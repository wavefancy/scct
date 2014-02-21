# Simulate the selection model of YRI and CEU
# Selection in YRI population.
# Simulate different number of individuals.

# Ne = 100000, u = 1.5 * 10^-8, r = 1.3 * 10^-8. L = 3m.
# theta = 4*Ne*u*L = 4*100000*1.5*10^-8*3*10^6 = 18000
# p = 4*Ne*r*L = 4 * 100000 * 1.3 * 10^-8 * 3 * 10^6 = 15600

# AF expansion:
# 200g/(4*100000) = 0.0005
# Ne_Africa_before expansion 24000 = 0.24

# EU expansion:
# 350/(4*100000) = 0.000875
# Ne_European_before expansion 7700 = 0.077

# EU out of africa: 3500/(4*100000) = 0.00875

# African ancestral expansion:
# 17000/(4*100000) = 0.0425
# Ne_before expansion: 12500 = 0.125

# Selection in YRI
# time: 0.005 = 2000/4/100000
# start freq. 1/100000 = 0.00001, in YRI (hard sweep)
# Selection coefficient: 1300

# individual number: from 30 to 210, step 30.


ss=`seq 30 30 210`
sAA=1300
for num in ${ss}
do
    let "saA=${sAA}/2"
    let "h_n=${num}*2"
    let "t_n=${h_n}*2"
    echo "java -jar -Xmx100G ~/jars/msms.jar ${t_n} 500 -t 18000 -r 15600 -N 100000 -I 2 ${h_n} ${h_n} -en 0.0005 1 0.24 -en 0.000875 2 0.077 -ej 0.00875 2 1 -en 0.0425 1 0.125 -SI 0.005 2 0.00001 0 -SAA ${sAA} -SaA ${saA} -Sp 0.5 -Smark -SFC -threads 30 | java -Xmx1G -jar ~/jars/MsDataSetSelectorV1.0.jar 0.5000000000 0.002 1 |gzip > AFR_CEU.sel_yri.n${num}.gz"
done
