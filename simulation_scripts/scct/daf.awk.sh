BEGIN{
OFS="\t";
}
{
    print $0,$2-$3    
}
