# Compute iHS

library(rehh)
library(multicore)

#para = "e0.3"
cmd_args = commandArgs();
para = "n.yri"
#para = cmd_args[4];

hap_file=paste(para,"/","hap_",para,"_",sep="");
map_file=paste(para,"/","map_",para,"_",sep="");

p_list = list();
#for( i in 1:10){
for( i in 1:300){
    p = c(paste(hap_file,i,sep=""), paste(map_file,i,sep=""));
    if( i==1){
        p_list=list(p);
    }else{
        p_list = c(p_list, list(p));    
    }
}

my_scan_hh = function(x){
    d = data2haplohh(hap_file=x[1],map_file=x[2])
    res = scan_hh(d)
}

#scan_res = mclapply(p_list[1:3], my_scan_hh, mc.cores=10)
scan_res = mclapply(p_list, my_scan_hh, mc.cores=25)

for( i in 1:length(scan_res)){
    if(i == 1){
        scan_raw = scan_res[[i]]
    }else{
        scan_raw = rbind(scan_raw, scan_res[[i]])    
    }
}

neutral_raw=scan_raw
#save(scan_raw,file=paste(para,"_scan_raw_combined.RData",sep=""))
save(neutral_raw,file=paste(para,"_scan_raw_all.RData",sep=""))

#n_raw="/picb/compgen/wangminxian/1000genome/selectionOnDNAMutation/validateSimulation/ihs/neutral_combined_raw.RData"
#load(n_raw)

#raw_all = rbind(scan_raw, neutral_raw);
#cmpute ihs
#ihs_result = ihh2ihs(raw_all);
ihs_result = ihh2ihs(scan_raw);

#save results
#write.table(ihs_result$res.ihs[1:dim(scan_raw)[1],],file=paste(para,"_ihs.txt",sep=""),quote=F,col.names=F);
write.table(ihs_result$res.ihs,file=paste(para,"_ihs.txt",sep=""),quote=F,col.names=F);



