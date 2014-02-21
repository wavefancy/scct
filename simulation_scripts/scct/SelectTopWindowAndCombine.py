#!/usr/bin/env python

'''
    Select top windows and combine overlap windows.
    
    @author: wavefancy@gmail.com
    @version: 1.0
    @since: 2013-04-26
    
    @usages:
    read from sys.stdin, and output to sys.stdout
'''

import sys

def help():
    sys.stderr.write('''
    Select top windows and combine overlap windows.
    
    @author: wavefancy@gmail.com
    @version: 1.0
    @since: 2013-04-26
    
    @usages:
    para1: proportion for top windows (eg. 0.01).
    para2: colmun index for chr.
    para3: colmun index for physical position start.
    para4: colmun index for physical position end.
    para5: colmun index for values.
    
    @Note:
    1. read from sys.stdin, and output to sys.stdout
    2. colmun index start from 1.
\n''')
    sys.exit(-1)


class Para(object):
    '''Common parameters settings.'''
    
    top = 0.01      # top 1%
    col_chr = 1     # column index for chr.
    col_pos_start = 2   # column index for physical position start.
    col_pos_end = 3     # column index for physical position end.
    col_val = 4     # column index for threshold values.
    
class Window(object):
    '''Window class'''
    def __init__(self, ):
        self.chr = 0
        self.pos_start = 0
        self.pos_end = 0
        self.val = 0
    
    def sort(a,b):
        '''Sort window, sort by chr, then by physical position.'''
        if (a.chr == b.chr):
            return a.pos_start - b.pos_start
        else:
            return cmp(a.chr,b.chr)
        
    def __str__(self, ):
        return '[%s,%d,%d,%d]'%(self.chr,self.pos_start,self.pos_end,self.val)
    
    
if __name__ == '__main__':
    if( len(sys.argv) != 6):
        help()
    
    # set parameters.
    Para.top = float(sys.argv[1])
    # shift column index.
    Para.col_chr = int(sys.argv[2]) -1
    Para.col_pos_start = int(sys.argv[3]) -1
    Para.col_pos_end = int(sys.argv[4]) -1
    Para.col_val = int(sys.argv[5]) -1
    
    windows = []
    
    for line in sys.stdin:
        line = line.strip()
        ss = line.split()
        if(len(ss) > 0):
            try:
                w = Window()
                w.chr = ss[Para.col_chr]
                w.pos_start = int(ss[Para.col_pos_start])
                w.pos_end = int(ss[Para.col_pos_end])
                w.val = int(ss[Para.col_val])
                
                windows.append(w)
            except ValueError:
                #print('skip')
                pass
    
    #sort by values.
    windows.sort(key=lambda x: x.val, reverse=True)
    #print(map(str, windows))
    
    top_Wins = windows[0: int(Para.top * len(windows))]
    if(len(top_Wins) <= 0):
        sys.exit(0)
    
    top_Wins.sort(cmp=Window.sort) #sort by chr, then by position
    #print(map(str, top_Wins))
    
    #Combine overlap regions.
    final_Wins = []
    
    final_Wins.append(top_Wins[0])
    for x in xrange(1,len(top_Wins)):
        if(top_Wins[x].chr == final_Wins[-1].chr and
           top_Wins[x].pos_start <= final_Wins[-1].pos_end):
            final_Wins[-1].pos_end = top_Wins[x].pos_end
        else:
            final_Wins.append(top_Wins[x])
    
    #output results.
    sys.stdout.write('#chr\tregion_start\tregion_end\n')
    for x in final_Wins:
        sys.stdout.write('%s\t%d\t%d\n'%(x.chr,x.pos_start,x.pos_end))
        
    sys.stdout.flush()
    sys.stdout.close()
    
    
    