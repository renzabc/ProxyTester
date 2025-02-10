import React, { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Textarea } from "@/components/ui/textarea"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"


function App() {
  const [proxyList, setProxyList] = useState('')


  let handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setProxyList(event.target.value)
  }

  // get text from textarea element

  // remove black lines

  let parseList = async (list: string) => {
    // Remove empty lines
    let cleanedList = list.split('\n').filter(line => line.trim() !== '').join('\n')
    // Display Removed lines in textarea
    setProxyList(cleanedList)

    let proxyArray = cleanedList.split("\n")
    console.log("Array Length: ", proxyArray.length)
    // Test all proxies on the List
    for (let i = 0; i < proxyArray.length; i++) {
      let prox = proxyArray[i].split(':')
      console.log(proxyArray[i], prox)
      let result = await testProxy(prox[0], prox[1], prox[2], prox[3], 'https://google.com')
      if (String(result) == 'Good') {
        console.log(proxyArray[i])
      }
      else if (String(result) == 'Bad') {

      }
      console.log('LAP')
    }

  }

  let testProxy = async (ip: string, port: string, username: string, password: string, url: string) => {
    return fetch("http://127.0.0.1:8081/test/proxy", {
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
                <div className='flex flex-row gap-2'>
                  <Input className=' text-white border-blue-300 ' placeholder='enter a url' />
                  <button className='text-blue-100 border border-blue-100 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                    onClick={() => { parseList(proxyList) }}
                  >GO</button>
                </div>
              </div>
            </div>

            <div id='result' className='border border-blue-500 w-[90vw] h-[40vh] max-h-[40vh] rounded-md'>

            </div>

            <div id='buttons' className='text-blue-100 flex flex-row justify-center'>
              <div className='flex justify-between w-[80vw] gap-2'>
                <button id='copyBad' className='border border-blue-300 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                  onClick={() => { }}
                >Copy Bad Proxies</button>
                <button id='copyGood' className='border border-blue-300 bg-neutral-900 hover:bg-neutral-800 rounded-lg px-2'
                  onClick={() => { }}
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
