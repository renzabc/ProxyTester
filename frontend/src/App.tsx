import React, { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Textarea } from "@/components/ui/textarea"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"


function App() {
  const [proxyList, setProxyList] = useState('')
  const [badList, setBadList] = useState<string[]>([])
  const [goodList, setGoodList] = useState<string[]>([])
  // const [displayGood, setDisplayGood] = useState(<></>)

  const displayGood = goodList.map((proxyString, index) => (
    <p className='text-green-400 px-1.5'>{proxyString}</p>
  ))

  let handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setProxyList(event.target.value)
  }

  let parseList = async (list: string) => {
    // Remove empty lines
    let cleanedList = list.split('\n').filter(line => line.trim() !== '').join('\n')
    // Display Removed lines in textarea
    setProxyList(cleanedList)

    setGoodList([]) // Clear goodList
    setBadList([])

    let proxyArray = cleanedList.split("\n")

    // Test all proxies on the List
    for (let i = 0; i < proxyArray.length; i++) {
      let prox = proxyArray[i].split(':')
      let result = await testProxy(prox[0], prox[1], prox[2], prox[3], url)

      if (String(result) === 'Good') {
        // Add to goodList and update state incrementally
        setGoodList((prevGoodList) => [...prevGoodList, proxyArray[i]])
      } else if (String(result) === 'Bad') {
        // Add to badList and update state incrementally
        setBadList((prevBadList) => [...prevBadList, proxyArray[i]])
      }
    }
  }



  let testProxy = async (ip: string, port: string, username: string, password: string, url: string) => {
    let result = await fetch("http://127.0.0.1:8081/test/proxy", {
      method: "POST",
      body: JSON.stringify({
        "ip": `${ip}`,
        "port": `${port}`,
        "username": `${username}`,
        "password": `${password}`,
        "url": `${url}`,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    });

    let res = await result.text()

    return res
  }



  const [url, setUrl] = useState('')

  let handleUrlChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUrl(event.target.value)
  }

  return (
    <>
      <div className='bg-blue-950 w-[100vw] h-[100vh] flex justify-center gap-2'>

        <div className='flex flex-col justify-center '>
          <div className='flex flex-col justify-center gap-2'>
            <h1 className='text-center text-blue-100 text-[25px]'>ProxyTester</h1>

            <div id='textEditpr' className='w-[90vw] h-[40vh]'>
              <div className="grid w-full gap-2">

                {/* <Textarea  /> */}
                <textarea className=' text-white rounded-md border border-blue-300 h-[33vh] max-h-[33vh] px-1.5 ' placeholder="Paste Proxies Here."
                  value={proxyList} onChange={handleTextChange}
                ></textarea>
                <div className='flex flex-row gap-2 -mt-0.5'>
                  <Input className=' text-white border-blue-300 ' placeholder='https://example.com' value={url} onChange={handleUrlChange} />
                  <button className='text-blue-100 border border-blue-100 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2 '
                    onClick={() => { parseList(proxyList) }}
                  >GO</button>
                </div>
              </div>
            </div>

            <div id='result' className='border border-blue-500 w-[90vw] h-[40vh] max-h-[40vh] rounded-md overflow-auto'>
              {displayGood}
            </div>

            <div id='buttons' className='text-blue-100 flex flex-row justify-center'>
              <div className='flex justify-between w-[80vw] gap-2'>
                <button id='copyBad' className='border border-blue-300 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                  onClick={() => {
                    let string = ''
                    badList.map((items, index) => {
                      string = string.concat(items)
                      string = string.concat("\n")
                    })
                    navigator.clipboard.writeText(string)
                  }}
                >Copy Bad Proxies</button>
                <button id='copyGood' className='border border-blue-300 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                  onClick={() => {
                    let string = ''
                    goodList.map((items, index) => {
                      string = string.concat(items)
                      string = string.concat("\n")
                    })
                    navigator.clipboard.writeText(string)
                  }}
                >Copy Good Proxies</button>
                <button id='reset' className='border border-blue-300 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                  onClick={() => { }}
                >Reset</button>
              </div>
            </div>
          </div>
        </div>

      </div >
    </>
  )
}

export default App
