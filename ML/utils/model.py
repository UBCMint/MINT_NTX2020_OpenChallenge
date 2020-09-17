from torch import nn
from myargs import args


class Simp_Model(nn.Module):
    def __init__(self, drop_p = 0.5):
        super().__init__()
        self.inputsize = args.patch_width * args.patch_height * args.patch_classes
        self.FC1 = nn.Linear(self.inputsize, 512)
        self.FC2 = nn.Linear(512, 256)
        self.FC3 = nn.Linear(256,128)
        self.FC4 = nn.Linear(128, 64)
        self.FC5 = nn.Linear(64, args.classes)
        self.relu = nn.ReLU()
        self.softmax = nn.Softmax(dim=0)
        self.dropout = nn.Dropout(p = drop_p)
        
    def forward(self, x):
        #x = x.view(x.size(0), -1)
        y = self.relu(self.FC1(x))
        y = self.dropout(y)
        y = self.relu(self.FC2(y))
        y = self.dropout(y)
        y = self.relu(self.FC3(y))
        y = self.dropout(y)
        y = self.relu(self.FC4(y))
        y = self.dropout(y)
        y = self.FC5(y)
        y = self.softmax(y)
        y = y.view(1, y.size(0))

        return y
