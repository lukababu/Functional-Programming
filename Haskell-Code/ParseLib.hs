--                                                                       
--         ParseLib.lhs

-- 	Library functions for parsing	
--         Note that this is not a monadic approach to parsing.	
--                                                                       		
--                   

module ParseLib where
import System.Environment
import System.IO  
import Control.Monad  
import System.Directory  
import Data.List  
import Data.String
import Data.Char
import System.Exit


------------------------------------------------------------------------------------------
----------------------------------------HELPER FUNCTIONS----------------------------------
------------------------------------------------------------------------------------------
-- deletes carriage return characters from a string
noCR :: String -> String
noCR (x:xs) | x == '\r' = noCR xs
    | otherwise = x: noCR xs
noCR []   = []

-- deletes whitespace strings from a list of strings
delWhitespaceLines :: [String] -> [String]
delWhitespaceLines [] = []
delWhitespaceLines (x:xs) | noEndSpaces x  == "" = delWhitespaceLines xs
                         | otherwise = x:(delWhitespaceLines xs)

noEndSpaces :: String -> String
noEndSpaces []  = []
noEndSpaces (x) | last x == ' '   = noEndSpaces (init x)
    | last x == '\t'  = noEndSpaces (init x)
    | otherwise   = x
    

--Returns the first arg in a list
infile :: [String] -> [String]
infile [] = error "No input fileName given!"
infile (x:_) = [x]


--Returns the slice of string from the original string
sliced :: String -> String -> [String] -> [String]
sliced _ _ [] = []
sliced [] x y = firsthalf x y
sliced w [] y = secondhalf w y
sliced w x y = secondhalf w (firsthalf x y)


--Returns the string without first half till designated String
firsthalf :: String -> [String] -> [String]
firsthalf _ [] = []
firsthalf [] x = x
firsthalf y (x:xs)    | y /= x = x:(firsthalf y xs)
                      | otherwise = []

--Returns the string without second half till designated String 
secondhalf :: String -> [String] -> [String]
secondhalf _ [] = []
secondhalf [] x = x
secondhalf y (x:xs) | y /= x = secondhalf y xs
                    | otherwise = xs

--Checks if the input Strings are actually pairs
twotup :: String -> Bool
twotup [] = False
twotup (x)  | ((head x) == '(') && ((last x) == ')') && ((commas x) == 1)  = True
            | otherwise   = False

--tocheck if the input strings in too near penalties is a 3 tuple
tuple3 :: String -> Bool
tuple3 [] = False
tuple3 (x)  | ((head x) == '(') && ((last x) == ')') && ((commas x) == 2)  = True
            | otherwise   = False

-- counts the commas in a string
commas :: String -> Int
commas []  = 0
commas (x:xs)  | (x == ',')  = 1 + (commas xs)
               | otherwise = 0 + (commas xs)
--Ands a list of bools
anding :: [Bool] -> Bool
anding [] = True
anding (x:xs) = x && (anding xs)

--Checks if it is a valid task
isTask :: Char -> Bool
isTask t  | t == 'A'  = True
          | t == 'B'  = True
          | t == 'C'  = True
          | t == 'D'  = True
          | t == 'E'  = True
          | t == 'F'  = True
          | t == 'G'  = True
          | t == 'H'  = True
          | otherwise   = False

--Converts machince to int
convertTask :: Char -> Int
convertTask t  | t == 'A' = 0
               | t == 'B' = 1
               | t == 'C' = 2
               | t == 'D' = 3
               | t == 'E' = 4
               | t == 'F' = 5
               | t == 'G' = 6
               | t == 'H' = 7
               | otherwise = -1

--Checks if machine is a valid machine
isMachine :: Char -> Bool
isMachine m  | m == '1'  = True
             | m == '2'  = True
             | m == '3'  = True
             | m == '4'  = True
             | m == '5'  = True
             | m == '6'  = True
             | m == '7'  = True
             | m == '8'  = True
             | otherwise   = False

--Converts machince to int
convertMac :: Char -> Int
convertMac m  | m == '1' = 0
              | m == '2' = 1
              | m == '3' = 2
              | m == '4' = 3
              | m == '5' = 4
              | m == '6' = 5
              | m == '7' = 6
              | m == '8' = 7
              | otherwise = -1

validMTPair :: String -> Bool
validMTPair [] = False
validMTPair ('(':m:',':t:')':[]) | isMachine m, isTask t = True
                                 | otherwise = False
validMTPair(x) = False

validTTtrip :: String -> Bool
validTTtrip [] = False
validTTtrip ('(':m:',':t:',':xs) | isTask m,isTask t  = True
                                 | otherwise = False
validTTtrip(x) = False

validTNTPair :: String -> Bool
validTNTPair [] = False
validTNTPair ('(':s:',':t:')':[]) | isTask s, isTask t = True && s /= t
                                  | otherwise = False
validTNTPair(x) = False

--isInt :: Char -> Bool

-- returns true if a list has duplicate values
duplicates :: (Eq a) => [a] -> Bool
duplicates [] = False
duplicates (x:xs) | (elem x xs) = True
                  | otherwise = duplicates xs

-- parses a [(task,task),...] into an [(int,int),...]
parseTT :: [String] -> [(Int,Int)]
parseTT []  = []
parseTT (('(':a:',':b:')':[]):xs) = ((convertTask a,convertTask b):(parseTT xs))
parseTT x = []

-- parses a [(mach,task),...] into an [(int,int),...]
parseMT :: [String] -> [(Int,Int)]
parseMT [] = []
parseMT (('(':a:',':b:')':[]):xs) = ((convertMac a,convertTask b):(parseMT xs))
parseMT x = []


-- parses a string into an (int,int,int) triple
parseTTx :: [String] -> [(Int,Int,Int)]
parseTTx [] = []
parseTTx (('(':a:',':b:',':c:')':[]):xs) = ((convertTask a,convertTask b,(ord c - 48)):(parseTTx xs))
parseTTx x = []

toToupleArray :: [[Int]] -> [(Int,Int,Int,Int,Int,Int,Int,Int)]
toToupleArray (x:xs) = ((x!!0),(x!!1),(x!!2),(x!!3),(x!!4),(x!!5),(x!!6),(x!!7)) : toToupleArray xs

tasktoChar :: Int -> [Char]
tasktoChar t
    | t == 0 = "A"
    | t == 1 = "B"
    | t == 2 = "C"
    | t == 3 = "D"
    | t == 4 = "E"
    | t == 5 = "F"
    | t == 6 = "G"
    | t == 7 = "H"
    | otherwise = "Z"
