--                                                                       
--         ParseLib.lhs

-- 	Library functions for parsing	
--         Note that this is not a monadic approach to parsing.	
--                                                                       
--         (c) Simon Thompson, 1995,1998.					
--                   

module ParseLib where
import System.Environment
import System.IO  
import Control.Monad  
import System.Directory  
import Data.List  
import Data.String
import System.Exit


------------------------------------------------------------------------------------------
----------------------------------------FUNCTIONS-----------------------------------------
------------------------------------------------------------------------------------------
-- deletes carriage return characters from a string
noCR :: String -> String
noCR (x:xs) | x == '\r' = noCR xs
    | otherwise = x: noCR xs
noCR []   = []

-- deletes whitespace strings from a list of strings
noWhitespaceLines :: [String] -> [String]
noWhitespaceLines [] = []
noWhitespaceLines (x:xs) | noEndSpaces x  == "" = noWhitespaceLines xs
                         | otherwise = x:(noWhitespaceLines xs)

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
