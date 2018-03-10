{-
    Driver File
    
	-}
import System.IO
import Control.Monad

data Machine = One | Two | Three | Four | Five | Six | Seven | Eight deriving (Read, Show, Enum, Eq, Ord)
data Task = A | B | C | D | E | F | G | H deriving (Read, Show, Enum, Eq, Ord)

type Name = [Char]
data Assisngment = Partial Machine Task | Forbidden Machine Task | NearTask Task Task | NearPenalities Task Task Machine deriving (Show)
type Penalities = [[Int]]

main = do
    handle <- openFile "test.txt" ReadMode
    contents <- hGetLine handle
    putStr contents

    hClose handle