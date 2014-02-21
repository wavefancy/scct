#!/usr/bin/env python

'''
    Annotate region(region no overlap) by values
    1. how many snps in this region
    2. how many snps with values bigger(smaller) than threshold.(>=,<=)
    3. proportion of #2/#1
    
    @author: wavefancy@gmail.com
    @version: 1.0
    @since: 2013-04-26
    
    @usages:
    read from sys.stdin, and output to sys.stdout
'''

import sys

def help():
    sys.stderr.write('''
    Annotate region(region no overlap) by values
    1. how many snps in this region
    2. how many snps with values bigger(smaller) than threshold.(>=,<=)
    3. proportion of #2/#1
    
    @author: wavefancy@gmail.com
    @version: 1.0
    @since: 2013-04-26
    
    @usages:
    para1: results file.
    para2: chr
    para3: colmun index for physical position
    para4: colmun index for values
    
    para5: window file
    para6: colmun index for chr.
    para7: colmun index for physical position start.
    para8: colmun index for physical position end.
    
    para9: threshold
    
    para10: whether transform to absolute value.[0|1: 1 transform]
    para11: bigger or smaller than threshold.[-1|1: -1 <=, 1 >=]
    
    @Note:
    1. read from sys.stdin, and output to sys.stdout
    2. colmun index start from 1.

\n''')
    sys.exit(-1)


class Para(object):
    '''Common parameters settings.'''
    
    r_file=''         # reults file
    r_col_pos = 1     # colmun index for physical position.
    r_chr = ''        # position
    r_col_val = 1     # colmun index for values
    
    w_file = ''       #window file
    w_col_chr = 1     # column index for chr.
    w_col_pos_start = 2   # column index for physical position start.
    w_col_pos_end = 3     # column index for physical position end.
    
    threshold = 2.0       # threshold values.
    
    transform = 1   #whether transform to absolute value
    bigger = 1  # >= or <=
    
    
class Window(object):
    '''Window class'''
    def __init__(self, ):
        self.chr = 0
        self.pos_start = 0
        self.pos_end = 0
        self.content = 0
    
    def sort(a,b):
        '''Sort window, sort by chr, then by physical position.'''
        if (a.chr == b.chr):
            return a.pos_start - b.pos_start
        else:
            return cmp(a.chr,b.chr)
        
    def __str__(self, ):
        return '[%s,%d,%d,%s]'%(self.chr,self.pos_start,self.pos_end,self.val)
    
    
if __name__ == '__main__':
    if( len(sys.argv) != 12):
        help()
    
    # set parameters.
    
    # shift column index.
    Para.w_file = sys.argv[5]
    Para.w_col_chr = int(sys.argv[6]) -1
    Para.w_col_pos_start = int(sys.argv[7]) -1
    Para.w_col_pos_end = int(sys.argv[8]) -1
    
    Para.r_file = sys.argv[1]
    Para.r_chr = sys.argv[2]
    Para.r_col_pos = int(sys.argv[3]) -1
    Para.r_col_val = int(sys.argv[4]) -1
    
    Para.threshold = float(sys.argv[9])
    
    Para.transform = int(sys.argv[10])
    Para.bigger = int(sys.argv[11])
    
    windows = []
    title=''
    for line in open(Para.w_file):
        line = line.strip()
        ss = line.split()
        if(len(ss) > 0):
            try:
                w = Window()
                w.chr = ss[Para.w_col_chr]
                w.pos_start = int(ss[Para.w_col_pos_start])
                w.pos_end = int(ss[Para.w_col_pos_end])
                w.content = line
                
                windows.append(w)
            except ValueError:
                if(len(title) <= 0):
                    title = line
    
    win_chr = [i for i in windows if i.chr == Para.r_chr] #extract windows only for chr.
    if(len(win_chr) <=0):
        sys.exit(0)
    
    results = [] # restore results for pos and val.
    #read results for results file.
    for line in open(Para.r_file):
        line = line.strip();
        ss = line.split()
        
        if(len(ss) > 0):
            try:
                pos = int(ss[Para.r_col_pos])
                val = float(ss[Para.r_col_val])
                
                if( Para.transform == 1):
                    results.append((pos, abs(val) )) # transhform val to absolute value.
                else:
                    results.append((pos, val ))
                    
            except Exception:
                pass
    
    #sort windows by chr then by pos
    win_chr.sort(cmp=Window.sort)
    
    pointer = 0
    
    if(len(title) > 0):
        sys.stdout.write(title)
        sys.stdout.write('\tSNP_NUM\tSIG_NUM\tFraction\n')
        
    for w in win_chr:
        start = 0; end = 0
        #find start.
        while(pointer < len(results)):
            if(results[pointer][0] < w.pos_start):
                pointer = pointer + 1
            else:
                start = pointer
                break
        
        while(pointer < len(results)):
            if(results[pointer][0] < w.pos_end):
                pointer = pointer +1
            else:
                end = pointer
                break
        
        snp_num = end - start + 1 #total number of snp in this region
        sig_num = 0 # significant snp number in this region.(value >= thresold)
        
        for i in xrange(start, end+1):
            
            if(Para.bigger == 1):    
                if(results[i][1] >= Para.threshold):
                    sig_num += 1
            else:
                if(results[i][1] <= Para.threshold):
                    sig_num += 1
            
        #output resuls.
        sys.stdout.write('%s\t%d\t%d\t%0.4f\n'%(w.content,snp_num,sig_num,sig_num*1.0/snp_num))
        
        
 
    sys.stdout.flush()
    sys.stdout.close()
